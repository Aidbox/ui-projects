import { describe, expect, test } from 'vitest'

describe('Patient', async () => {
  test('Create /Patient', async () => {

    let resp;

    resp = await http({method: "POST",
                       url: "/$sql",
                       body: ["truncate patient"]})

    resp = await http({method: "GET",
                       url: "/Patient"});

    expect(resp.total).toBe(0);

    resp = await http({method: "POST",
                       url: "/Patient",
                       body: {name: [{given: ["John"]}]}});

    resp = await http({method: "GET",
                       url: "/Patient"});

    expect(resp.total).toBe(1);
  })
})
